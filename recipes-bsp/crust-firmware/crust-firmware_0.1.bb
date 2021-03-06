#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-bsp/crust-firmware/crust-firmware_0.1.bb

SUMMARY = "Crust Firmware"
DESCRIPTION = "Crust Libre SCP firmware for Allwinner sunxi SoCs."
LICENSE = "BSD-1-Clause & BSD-3-Clause & GPLv2 & MIT"
LIC_FILES_CHKSUM = 'file://LICENSE.md;md5=ce7d78bbe29bbf107647692966b8209b'

inherit deploy

DEPENDS = "flex-native bison-native"

BRANCH = "master"
SRCREV ?= "23d6d7b4fbb5375845d96f622e82435064343098"
SRC_URI = " \
    git://github.com/crust-firmware/crust.git;protocol=https;branch=${BRANCH} \
    https://musl.cc/or1k-linux-musl-cross.tgz \
    "

S = "${WORKDIR}/git"

SRC_URI[sha256sum] = "49751856f47b3f6ee468ea867acb6bf482b1b4aff57257de482e8d1c9f38db94"

PACKAGE_ARCH = "${MACHINE_ARCH}"

COMPATIBLE_MACHINE = "pinephone"
PLATFORM_pinephone = "sun50i_a64"

# Let the Makefile handle setting up the CFLAGS and LDFLAGS as it is a standalone application
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure() {
	make BUILDCC=${BUILD_CC} BUILDAR=${BUILD_AR} LEX=flex YACC=yacc CROSS_COMPILE=${WORKDIR}/or1k-linux-musl-cross/bin/or1k-linux-musl- HOST_COMPILE=${TARGET_PREFIX} pinephone_defconfig
}

do_compile() {
	make BUILDCC=${BUILD_CC} BUILDAR=${BUILD_AR} LEX=flex YACC=yacc CROSS_COMPILE=${WORKDIR}/or1k-linux-musl-cross/bin/or1k-linux-musl- HOST_COMPILE=${TARGET_PREFIX} build/scp/scp.bin
}

do_install() {
	:
}

do_deploy() {
	install -m 0644 ${S}/build/scp/scp.elf ${DEPLOYDIR}/scp-${MACHINE}.elf
	install -m 0644 ${S}/build/scp/scp.bin ${DEPLOYDIR}/scp-${MACHINE}.bin
}

addtask deploy before do_build after do_compile
