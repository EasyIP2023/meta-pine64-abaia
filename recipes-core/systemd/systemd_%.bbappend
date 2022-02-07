# Enable external services
# Enable udev service for /dev filesystem directory management
SYSTEMD_SERVICE_${PN} = "${PN}-udevd.service"

# Disable the login console (getty@tty1)
do_install:append() {
  rm ${D}${systemd_unitdir}/system/getty@.service
  rm ${D}${systemd_unitdir}/system/getty-pre.target
}
