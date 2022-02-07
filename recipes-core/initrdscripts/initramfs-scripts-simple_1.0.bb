#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-core/initrdscripts/initramfs-scripts-simple_1.0.bb

DESCRIPTION = "Simple init script to boot on a pinephone device"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES = "${PN}"

SRC_URI = " \
  file://init.sh \
  "

do_install() {
  install -m 0755 ${WORKDIR}/init.sh ${D}/init
}

FILES_${PN} = "/init"
