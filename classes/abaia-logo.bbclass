# imagemagick: for image resizing
# netpbm: for generating proper ppm that the kernel can understand
DEPENDS_append = " imagemagick-native netpbm-native"

RAW_GRAPHIC_FILE_RES ?= ""
RAW_GRAPHIC_FILE ?= ""
LOGO_PPM_FILE ?= ""
LOGO_PPM_FILE_LOC ?= ""
LOGO_BPM_FILE ?= ""

# 1. Resize image to WxH
# 2. Because the bootlogo is limited to 224 colors, ppmquant will reduce the image to 224 colors.
# 3. pamtopnm -plain converts to results to a Format that the kernel understands
do_swap_logo() {
  local W=$(echo "${RAW_GRAPHIC_FILE_RES}" | cut -d 'x' -f1)
  local H=$(echo "${RAW_GRAPHIC_FILE_RES}" | cut -d 'x' -f2)

  bbdebug 1 "Resizing ${RAW_GRAPHIC_FILE} to ${W}x${H}"

  # resize input image to WxH
  convert.im7 ${WORKDIR}/${RAW_GRAPHIC_FILE} -resize ${W}x${H} -compress None ${WORKDIR}/temp-logo.ppm
  if [ $? -ne 0 ]; then
    bberror "Failed: Resize ${RAW_GRAPHIC_FILE} to ${W}x${H} and convert to ${LOGO_PPM_FILE}"
    return 1
  fi

  ppmquant 224 ${WORKDIR}/temp-logo.ppm > ${WORKDIR}/temp2-logo.ppm
  if [ $? -ne 0 ]; then
    bberror "Failed: tppmquant 224 ${WORKDIR}/temp-logo.ppm"
    return 1
  fi

  pamtopnm -plain ${WORKDIR}/temp2-logo.ppm > ${LOGO_PPM_FILE_LOC}/${LOGO_PPM_FILE}
  if [ $? -ne 0 ]; then
    bberror "Failed: pnmtopnm ${WORKDIR}/temp2-logo.ppm"
    return 1
  fi

  if [ -n "${LOGO_BPM_FILE}" ]; then
    pamseq 1 224 > ${WORKDIR}/mapfile.pgm
    if [ $? -ne 0 ]; then
      bberror "Failed: pamseq ${WORKDIR}/mapfile.pgm"
      return 1
    fi

    ppmtobmp -mapfile=${WORKDIR}/mapfile.pgm -bpp=24 ${LOGO_PPM_FILE_LOC}/${LOGO_PPM_FILE} > ${LOGO_PPM_FILE_LOC}/${LOGO_BPM_FILE}
    if [ $? -ne 0 ]; then
      bberror "Failed: ppmtobmp ${LOGO_PPM_FILE_LOC}/${LOGO_PPM_FILE}"
      return 1
    fi
  fi

  return 0
}

addtask swap_logo after do_configure before do_compile
