# Abaia OS

Bitbake layer for building personal linux distro for the pinephone.

## Dependencies

* URI: https://github.com/openembedded/openembedded-core
    * branch: dunfell
    * revision: HEAD
* URI: https://github.com/openembedded/bitbake
    * branch: master
    * revision: 1.46
* URI: https://git.openembedded.org/meta-openembedded
    * branch: dunfell
    * revision: HEAD
* URI: https://github.com/EasyIP2023/meta-random
    * branch: dunfell
    * revision: HEAD

## Build/Install

```
bitbake-layers add-layer meta-openembedded/meta-oe
bitbake-layers add-layer meta-pine64-abaia
bitbake-layers add-layer meta-random
DISTRO=abaia MACHINE=pinephone bitbake core-image-base
```

For flashing [pinephone eMMC](https://wiki.pine64.org/index.php/PinePhone_Installation_Instructions).

```
sudo bmaptool copy --bmap tmp/deploy/images/pinephone/core-image-base-pinephone.wic.bmap tmp/deploy/images/pinephone/core-image-base-pinephone.wic.gz <block device>
```
