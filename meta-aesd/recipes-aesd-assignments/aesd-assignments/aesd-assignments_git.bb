# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-kevrocks67.git;protocol=https;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV =  "df90f24a7cd4dd487352c65d05a77dbb61dd63cb"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket ${sysconfdir}/init.d/S99aesdsocket"

INITSCRIPT_NAME:${PN} = "S99aesdsocket"
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_PARAMS = "defaults 99"

TARGET_LDFLAGS += "-pthread -lrt"
TARGET_CC_ARCH += "${LDFLAGS}"

inherit update-rc.d

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
    install -d ${D}${bindir}
    install -d ${D}/etc/init.d/

	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/S99aesdsocket
}
