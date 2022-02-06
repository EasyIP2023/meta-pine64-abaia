#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-bsp/trusted-firmware-a/trusted-firmware-a_2.6.bb

SUMMARY = "Arm Trusted Firmware (ATF)"
DESCRIPTION = "Trusted Firmware-A (TF-A) provides a reference implementation of secure world software for Armv7-A and Armv8-A, including a Secure Monitor executing at Exception Level 3 (EL3)."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = 'file://license.rst;md5=1dd070c98a281d18d9eefd938729b031'

inherit deploy

PROVIDES = "virtual/${PN}"

BRANCH = "master"
SRCREV ?= "a1f02f4f3daae7e21ee58b4c93ec3e46b8f28d15"
SRC_URI = " \
  git://git.trustedfirmware.org/TF-A/trusted-firmware-a.git;protocol=https;branch=${BRANCH} \
  file://0002-allwinner-Choose-PSCI-states-to-avoid-translation.patch \
  file://0003-allwinner-Simplify-CPU_SUSPEND-power-state-encoding.patch \
  "

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "pinephone|pinephonepro"

PLATFORM:pinephone = "sun50i_a64"
PLATFORM:pinephonepro = "rk3399"

BL_FILENAME:pinephone = "${MACHINE}"
BL_FILENAME:pinephonepro = "${PLATFORM}"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure() {
	:
}

do_compile() {
	oe_runmake -C ${S} BUILD_BASE=${B} CROSS_COMPILE="${TARGET_PREFIX}" PLAT="${PLATFORM}" bl31
}

do_install() {
	:
}

do_deploy() {
	install -m 0644 ${S}/${PLATFORM}/release/bl31/bl31.elf ${DEPLOY_DIR_IMAGE}/bl31-${BL_FILENAME}.elf
	if [ -f "${S}/${PLATFORM}/release/bl31.bin" ]; then
		install -m 0644 ${S}/${PLATFORM}/release/bl31.bin ${DEPLOY_DIR_IMAGE}/bl31-${BL_FILENAME}.bin
	fi
}

addtask deploy before do_build after do_compile
