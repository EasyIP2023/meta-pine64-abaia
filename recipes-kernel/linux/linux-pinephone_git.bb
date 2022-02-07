#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-kernel/linux/linux-pinephone_git.bb

DESCRIPTION = "Pine64 Linux Kernel"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${S}/COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

inherit kernel abaia-logo
require recipes-kernel/linux/linux-yocto.inc

RAW_GRAPHIC_FILE_RES = "512x512"
RAW_GRAPHIC_FILE = "1920x1080_demon-slayer.jpg"
LOGO_PPM_FILE = "logo_linux_clut224.ppm"
LOGO_PPM_FILE_LOC = "${S}/drivers/video/logo/"

LINUX_VERSION ?= "5.14"
LINUX_VERSION_EXTENSION = "-pinephone"

PV = "${LINUX_VERSION}-git${SRCPV}"

KERNEL_VERSION_SANITY_SKIP="1"
# KBUILD_DEFCONFIG:pinephone = "pinephone_defconfig"

LINUX_BRANCH = "orange-pi-${LINUX_VERSION}"
LINUX_KMETA_BRANCH = "yocto-${LINUX_VERSION}"
SRCREV:machine = "4b9c91ad20cb5fcfc068a872b17c3cdf758088f6"
SRCREV:meta = "31cc1e7517184bdfcd40e40c4dd3cf75583d3414"
KMETA = "kernel-meta"
SRC_URI = " \
    git://github.com/megous/linux;protocol=https;branch=${LINUX_BRANCH};name=machine \
    git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=${LINUX_KMETA_BRANCH};destsuffix=${KMETA};name=meta \
    file://0001-dts-pinephone-drop-modem-power-node.patch \
    file://0002-dts-pinephone-jack-detection.patch \
    file://defconfig \
    file://extra.cfg \
    file://${RAW_GRAPHIC_FILE} \
    "

COMPATIBLE_MACHINE = "pinephonepro|pinephone"
