#@ORIGINAL: https://github.com/webOS-ports/meta-pine64-luneos/blob/master/recipes-core/images/initramfs-uboot-image.bb

SUMMARY = "Initramfs image to boot from internal flash with initramfs, using uboot"
DESCRIPTION = "Provides a minimal environment to bootstrap and run LuneOS from U-Boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

IMAGE_INSTALL = "busybox base-passwd bash"
IMAGE_INSTALL:append = " initramfs-scripts-simple"
IMAGE_FEATURES = ""
IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"
export IMAGE_BASENAME = "initramfs-uboot-image"
IMAGE_LINGUAS = ""

IMAGE_FSTYPES:forcevariable = "cpio.gz.u-boot"
IMAGE_FSTYPES:forcevariable:pinephone = "cpio.gz.u-boot"

# We don't need depmod data here
KERNELDEPMODDEPEND = ""
USE_DEPMOD = "0"

inherit core-image
