# Abaia OS

Bitbake layer for building personal linux distro for the pinephone.

## Dependencies

* URI: https://github.com/openembedded/openembedded-core
    * branch: dunfell
    * revision: HEAD

* URI: https://github.com/openembedded/bitbake
    * branch: master
    * revision: 1.46

## Build/Install

```
bitbake-layers add-layer meta-pine64-abaia
DISTRO=abaia MACHINE=pinephone bitbake core-image-base
```

For flashing [pinephone eMMC](https://wiki.pine64.org/index.php/PinePhone_Installation_Instructions).

```
sudo bmaptool copy --bmap tmp/deploy/images/pinephone/core-image-base-pinephone.wic.bmap tmp/deploy/images/pinephone/core-image-base-pinephone.wic.gz <block device>
```
