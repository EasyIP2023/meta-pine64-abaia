#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-bsp/u-boot/u-boot_%25.bbappend

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

LIC_FILES_CHKSUM:pinephone = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"
LIC_FILES_CHKSUM:pinephonepro = "file://Licenses/README;md5=5a7450c57ffe5ae63fd732446b988025"

SRCREV:pinephone = "880f5035decae44a5be943875f35ed9d9efc011d"
SRC_URI:pinephone = " \
    git://gitlab.com/pine64-org/u-boot.git;protocol=https;branch=crust \
    file://0001-sunxi-h3-Fix-PLL1-setup-to-never-use-dividers.patch \
    file://boot.txt \
    file://${RAW_GRAPHIC_FILE} \
    "

DEPENDS:append:pinephone = " virtual/trusted-firmware-a crust-firmware u-boot-tools-native python3-setuptools-native"

ATF_DEPENDS ??= ""

EXTRA_OEMAKE:append:pinephone = " BL31=${DEPLOY_DIR_IMAGE}/bl31-pinephone.bin"
ATF_DEPENDS:pinephone = " virtual/trusted-firmware-a:do_deploy crust-firmware:do_deploy"

do_configure[depends] .= "${ATF_DEPENDS}"

do_configure:prepend:pinephone() {
  sed -i "s:#KARGS#:${KERNEL_ARGS}:g" "${WORKDIR}/boot.txt"
  echo "CONFIG_DM_VIDEO=y"            >> ${S}/configs/pinephone_defconfig
  echo "CONFIG_CMD_BMP=y"             >> ${S}/configs/pinephone_defconfig
  echo "CONFIG_BMP_24BPP"             >> ${S}/configs/pinephone_defconfig
  echo "CONFIG_SPLASH_SCREEN=y"       >> ${S}/configs/pinephone_defconfig
  echo "CONFIG_SPLASH_SCREEN_ALIGN=y" >> ${S}/configs/pinephone_defconfig

  # Insert the ATF binary
  if [ ! -f ${B}/bl31.bin ]; then
    ln ${DEPLOY_DIR}/images/${MACHINE}/bl31-${MACHINE}.bin ${B}/bl31.bin
  fi

  # Insert the Crust binary
  if [ ! -f ${B}/scp.bin ]; then
    ln ${DEPLOY_DIR}/images/${MACHINE}/scp-${MACHINE}.bin ${B}/scp.bin
  fi

  mkimage -A arm -O linux -T script -C none -n "U-Boot boot script" \
      -d ${WORKDIR}/boot.txt ${WORKDIR}/boot.scr
}

inherit abaia-logo

RAW_GRAPHIC_FILE_RES = "512x512"
RAW_GRAPHIC_FILE = "1920x1080_demon-slayer.jpg"
LOGO_PPM_FILE = "abaia-logo.ppm"
LOGO_BPM_FILE = "abaia-logo.bmp"
LOGO_PPM_FILE_LOC = "${DEPLOY_DIR_IMAGE}"

FILES_${PN}:append:pinephone = " /boot/boot.scr"
