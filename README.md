<div align="center">
  <h1>🧘 ZenSkip</h1>
  <p><b>Never touch your phone to skip an ad again.</b></p>
  
  [![Build Status](https://github.com/namit081/ZenSkip/actions/workflows/android.yml/badge.svg)](https://github.com/namit081/ZenSkip/actions/workflows/android.yml)
  [![Latest Release](https://img.shields.io/github/v/release/namit081/ZenSkip)](https://github.com/namit081/ZenSkip/releases/latest)
  [![Platform](https://img.shields.io/badge/platform-Android-3DDC84.svg?logo=android)](https://www.android.com)
  [![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
</div>

---

## 📺 The Problem
You're relaxing on the couch. You've just cast a great video to your TV. You sink into the cushions, finally comfortable. Then, suddenly... 

**BAM.** A blaring, 2-minute unskippable-until-5-seconds ad interrupts your peace. 

To make it stop, you have to find your phone, unlock the screen, wait for the tiny "Skip Ad" button to appear, and tap it. It's infuriating. 

## 🥷 The Solution: ZenSkip
ZenSkip is a tiny, battery-friendly Android app that completely eliminates this problem. It runs silently in the background and uses Android's Accessibility Services to "watch" the screen for you. 

The moment a "Skip ad" or "Skip ads" button appears on YouTube, **ZenSkip instantly taps it for you.** You don't even have to look at your phone.

### ✨ Features
* **Zero Configuration:** Just install, enable the accessibility permission, and forget it exists.
* **Smart Active Indicator:** The app dynamically detects if the Accessibility permission is enabled and guides you.
* **Skip Stats Dashboard 📊:** It persistently counts every single ad it skips! View your Daily, Monthly, and All-Time skips.
* **Visual Log History:** Curios what you missed? ZenSkip silently captures and saves a screenshot at the exact moment it clicks the skip button. Scroll through a visual history of recently skipped ads!
* **Battery Friendly:** Extremely lightweight. It only activates when the target app is open.
* **100% Local & Safe:** It requires **NO internet permissions**. It doesn't track you. It doesn't send data anywhere.

## 🚀 Download & Installation

**[👉 Download ZenSkip v1.2.1 APK Here](https://github.com/namit081/ZenSkip/releases/latest)**

### Setup Instructions
1. Download the `.apk` file to your Android phone.
2. Open the file to install it. (You may be prompted to allow "Install Unknown Apps" from your browser/file manager).
3. Once installed, open the **ZenSkip** app from your app drawer.
4. Tap the **"Open Accessibility Settings"** button.
5. In your phone's Accessibility settings, find **ZenSkip**.
6. Turn the switch **ON** and allow the permission.

That's it! Go back to the couch and enjoy your videos. ZenSkip will handle the rest.

## 🧪 Testing & Reliability
ZenSkip is built for extreme reliability. We utilize an extensive automated testing pipeline via **GitHub Actions**, heavily leveraging Robolectric to simulate Android frameworks in headless CI/CD environments. 
- **Text-Matching Test Suite:** Rigorously validates permutations of UI text (e.g. "Skip ad", "Skip video", "skip") while ensuring false positives (e.g. "Do not skip") are ignored.
- **Stats & Storage Suite:** Validates the persistence logic and ensures the automated screenshot cleanup safely deletes older image files to preserve device storage.

## 🤝 Contributing
Feel free to open an issue or submit a pull request if you want to improve ZenSkip!

---
*Disclaimer: ZenSkip is an independent open-source project and is not affiliated with Google or YouTube.*
