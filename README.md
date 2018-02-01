# m.s-aluminium-manager-app
אפליקציה למעקב וניהול פרויקטים למנהלי עבודה בחברת מ.ש אלומיניום

<img src="https://github.com/meitarsh/m.s-aluminium-manager-app/blob/master/pics/logo.jpg" height="300" width="400">

## Disclaimer

* This repository represents our final project as seniors in the B.Sc in Software Engineering program at Jerusalem College of Engineering at the year of 2017-2018.

* This project lifecycle starts at the end of second semester in our 3rd years till the end of the second semester in our 4th year.

* This repository will include all the documents, code, guides and photos that are relevent for the final product

* During release stages, release application will be uploaded to this repostiroy and the release tab.

* This project depends on [Android SDK](https://developer.android.com/studio/index.html) in order to compile and run the application

* This project depends on [JTDS](http://jtds.sourceforge.net/), a 100% pure java free & open-source JDBC driver for remote MSSQL databases, JTDS is licensed under [LGPL License](https://www.gnu.org/copyleft/lesser.html), read [JTDS license](http://jtds.sourceforge.net/license.html) here, this project won't change JTDS driver functionality, but is based on it, thus is shared under free & open source license, we didn't change JTDS, thus we are free to choose to MIT as our license.

* This project also depends on [ics-openVPN](https://github.com/schwabe/ics-openvpn), a port of [openVPN](https://openvpn.net/) for Android for VPN functionality,  [ics-openVPN](https://github.com/schwabe/ics-openvpn) is licensed under [GPLv2](https://www.gnu.org/licenses/old-licenses/gpl-2.0.html) therefore this project, if it changes ics-openVPN, must distribute its changes.

## Staff
* Main project developer repository masters

Zeev Melumuian
<img src="https://avatars3.githubusercontent.com/u/25983708?s=400&u=b8851ea42c6dc967d131b307a0b99074a84a851e&v=4" height="100" width="80">
 Meitar Shukrun
 <img src="https://avatars1.githubusercontent.com/u/26038128?s=400&v=4" height="100" width="80">

* Acedamic advisor
  Shay Tavor

 |Category|Status|
|---|---|
| Version Control System| [![git](https://img.shields.io/badge/Version%20Control-Git-green.svg)](https://git-scm.com/) & [![github](https://img.shields.io/badge/Version%20Control-Github-green.svg)](https://github.com/) |
| Recommended IDE | [![Android Studio](https://img.shields.io/badge/IDE-Android%20Studio-green.svg)](https://developer.android.com/studio/index.html) |
| License | [![Open Source Love](https://badges.frapsoft.com/os/mit/mit.svg?v=102)](https://github.com/meitarsh/m.s-aluminium-manager-app/) |
| Code Style | [![XO code style](https://img.shields.io/badge/code_style-XO-5ed9c7.svg)](https://github.com/meitarsh/m.s-aluminium-manager-app) |
| Issues | [![GitHub issues](https://img.shields.io/github/issues/meitarsh/m.s-aluminium-manager-app.svg?style=flat)](https://github.com/meitarsh/m.s-aluminium-manager-app/issues) |
| Project Management Board| [![here](https://img.shields.io/badge/Project%20Management%20Board-On%20demand-lightgrey.svg)](https://github.com/meitarsh/m.s-aluminium-manager-app/projects/1) |
| Documnetation | [![Inline docs](http://inch-ci.org/github/meitarsh/m.s-aluminium-manager-app.svg?branch=master)](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Documentation) |
| Diary |  [![link](https://img.shields.io/badge/Diary-On%20demand-blue.svg)](https://calendar.google.com/calendar/embed?src=t1f2ojv5arrqonei6h09i5fld0%40group.calendar.google.com&ctz=Europe/Athens) |
| Releases |  [![release](http://github-release-version.herokuapp.com/github/meitarsh/m.s-aluminium-manager-app/release.svg?style=flat)](https://github.com/meitarsh/m.s-aluminium-manager-app/releases/latest) |
| Codecy Code Review | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ba0470e040144e08e6a235be2940a13)](https://www.codacy.com/app/trunks_ishter/m.s-aluminium-manager-app?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=meitarsh/m.s-aluminium-manager-app&amp;utm_campaign=Badge_Grade) |
| our written API! | [![Application API](https://img.shields.io/badge/Application-API-blue.svg)](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/API) |
| our code documentation | [![Code Doc](https://img.shields.io/badge/Code-doc-blue.svg)](http://htmlpreview.github.io/?https://github.com/meitarsh/m.s-aluminium-manager-app/blob/master/docs/javadoc/index.html) |

## Dependecies
   * [Android SDK](https://developer.android.com/index.html) - as an Android Application, we muse use that.
   * [Gradle build tool](https://gradle.org/) - to build the application, Gradle tool provides us a scripted makefile and build tools.
   * [Kotlin runtime](https://kotlinlang.org/)- Is responsible of exporting bytecode from our kotlin code to the ART.
      	Along with:
	* [Kotlin](https://kotlinlang.org/api/latest/jvm/stdlib/index.html) stdlib.
	* [Kotlin](https://kotlinlang.org/docs/tutorials/android-plugin.html) android extension   .  
   * [JUnit](http://junit.org/junit5/) - for basic java unit testing.
   * [Espresso](https://developer.android.com/training/testing/espresso/index.html) - extension for testing from JUnit to provide Android envoirment of unit testing.
   * [JTDS](http://jtds.sourceforge.net/) - the JDBC (Java Database driver) we use to communicate with MSSQL server.
   * [scytale](https://github.com/yakivmospan/scytale) - simplifies encryption and key storage using application sign to hide the key.
   * [Dokka](https://github.com/Kotlin/dokka) - JDOC auto-generation.
	

## [Software Design](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Software-Design)

## [Iterations](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iterations)

* [Iteration 0 ZFR](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iteration-0-ZFR)
* [Iteration 1 MVP](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iteration-1-MVP)
* [Iteration 2 alpha](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iteration-2-alpha)
* [Iteration 3 beta](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iteration-3-beta)
* [Iteration 4 final](https://github.com/meitarsh/m.s-aluminium-manager-app/wiki/Iteration-4-final)
