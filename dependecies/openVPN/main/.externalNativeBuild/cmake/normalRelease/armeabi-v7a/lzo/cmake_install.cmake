# Install script for directory: /home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo

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
    set(CMAKE_INSTALL_CONFIG_NAME "Release")
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
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/share/doc/lzo/AUTHORS;/usr/local/share/doc/lzo/COPYING;/usr/local/share/doc/lzo/NEWS;/usr/local/share/doc/lzo/THANKS;/usr/local/share/doc/lzo/LZO.FAQ;/usr/local/share/doc/lzo/LZO.TXT;/usr/local/share/doc/lzo/LZOAPI.TXT")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/share/doc/lzo" TYPE FILE FILES
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/AUTHORS"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/COPYING"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/NEWS"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/THANKS"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/doc/LZO.FAQ"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/doc/LZO.TXT"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/doc/LZOAPI.TXT"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/include/lzo/lzo1.h;/usr/local/include/lzo/lzo1a.h;/usr/local/include/lzo/lzo1b.h;/usr/local/include/lzo/lzo1c.h;/usr/local/include/lzo/lzo1f.h;/usr/local/include/lzo/lzo1x.h;/usr/local/include/lzo/lzo1y.h;/usr/local/include/lzo/lzo1z.h;/usr/local/include/lzo/lzo2a.h;/usr/local/include/lzo/lzo_asm.h;/usr/local/include/lzo/lzoconf.h;/usr/local/include/lzo/lzodefs.h;/usr/local/include/lzo/lzoutil.h")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/include/lzo" TYPE FILE FILES
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1a.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1b.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1c.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1f.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1x.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1y.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo1z.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo2a.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzo_asm.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzoconf.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzodefs.h"
    "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/src/main/cpp/lzo/include/lzo/lzoutil.h"
    )
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/lib/liblzo2.a")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/lib" TYPE STATIC_LIBRARY FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/liblzo2.a")
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/libexec/lzo/examples/lzopack")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/libexec/lzo/examples" TYPE EXECUTABLE FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/lzopack")
  if(EXISTS "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzopack" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzopack")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/home/chaosruler/Android/Sdk/ndk-bundle/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-strip" "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzopack")
    endif()
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/libexec/lzo/examples/lzotest")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/libexec/lzo/examples" TYPE EXECUTABLE FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/lzotest")
  if(EXISTS "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzotest" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzotest")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/home/chaosruler/Android/Sdk/ndk-bundle/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-strip" "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/lzotest")
    endif()
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/libexec/lzo/examples/simple")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/libexec/lzo/examples" TYPE EXECUTABLE FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/simple")
  if(EXISTS "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/simple" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/simple")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/home/chaosruler/Android/Sdk/ndk-bundle/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-strip" "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/simple")
    endif()
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/libexec/lzo/examples/testmini")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/libexec/lzo/examples" TYPE EXECUTABLE FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/testmini")
  if(EXISTS "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/testmini" AND
     NOT IS_SYMLINK "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/testmini")
    if(CMAKE_INSTALL_DO_STRIP)
      execute_process(COMMAND "/home/chaosruler/Android/Sdk/ndk-bundle/toolchains/arm-linux-androideabi-4.9/prebuilt/linux-x86_64/bin/arm-linux-androideabi-strip" "$ENV{DESTDIR}/usr/local/libexec/lzo/examples/testmini")
    endif()
  endif()
endif()

if("${CMAKE_INSTALL_COMPONENT}" STREQUAL "Unspecified" OR NOT CMAKE_INSTALL_COMPONENT)
  list(APPEND CMAKE_ABSOLUTE_DESTINATION_FILES
   "/usr/local/lib/pkgconfig/lzo2.pc")
  if(CMAKE_WARN_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(WARNING "ABSOLUTE path INSTALL DESTINATION : ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
  if(CMAKE_ERROR_ON_ABSOLUTE_INSTALL_DESTINATION)
    message(FATAL_ERROR "ABSOLUTE path INSTALL DESTINATION forbidden (by caller): ${CMAKE_ABSOLUTE_DESTINATION_FILES}")
  endif()
file(INSTALL DESTINATION "/usr/local/lib/pkgconfig" TYPE FILE FILES "/home/chaosruler/Desktop/m.s-aluminium-manager-app/src/openVPN/main/.externalNativeBuild/cmake/normalRelease/armeabi-v7a/lzo/lzo2.pc")
endif()

