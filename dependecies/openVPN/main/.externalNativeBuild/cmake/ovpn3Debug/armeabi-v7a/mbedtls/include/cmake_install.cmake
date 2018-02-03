# Install script for directory: /home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/usr/local")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "Debug")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Install shared libraries without execute permission?
if(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  set(CMAKE_INSTALL_SO_NO_EXE "0")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/mbedtls" TYPE FILE PERMISSIONS OWNER_READ OWNER_WRITE GROUP_READ WORLD_READ FILES
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/aes.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/aesni.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/arc4.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/asn1.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/asn1write.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/base64.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/bignum.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/blowfish.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/bn_mul.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/camellia.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ccm.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/certs.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/check_config.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/cipher.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/cipher_internal.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/cmac.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/compat-1.3.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/config.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ctr_drbg.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/debug.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/des.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/dhm.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ecdh.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ecdsa.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ecjpake.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ecp.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ecp_internal.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/entropy.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/entropy_poll.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/error.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/gcm.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/havege.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/hmac_drbg.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/md.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/md2.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/md4.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/md5.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/md_internal.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/memory_buffer_alloc.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/net.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/net_sockets.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/oid.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/padlock.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pem.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pk.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pk_internal.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pkcs11.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pkcs12.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/pkcs5.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/platform.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/platform_time.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ripemd160.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/rsa.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/sha1.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/sha256.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/sha512.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl_cache.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl_ciphersuites.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl_cookie.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl_internal.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/ssl_ticket.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/threading.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/timing.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/version.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/x509.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/x509_crl.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/x509_crt.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/x509_csr.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/mbedtls/include/mbedtls/xtea.h"
    )
endif()

