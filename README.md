# 🎨Color Picker Library for Android

[![Download](https://api.bintray.com/packages/dhaval2404/maven/colorpicker/images/download.svg) ](https://bintray.com/dhaval2404/maven/colorpicker/_latestVersion)
[![Releases](https://img.shields.io/github/release/dhaval2404/colorpicker/all.svg?style=flat-square)](https://github.com/Dhaval2404/ColorPicker/releases)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
![Language](https://img.shields.io/badge/language-Kotlin-orange.svg)
[![PRWelcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/Dhaval2404/ColorPicker)
[![Twitter](https://img.shields.io/twitter/url/https/github.com/Dhaval2404/ImagePicker.svg?style=social)](https://twitter.com/intent/tweet?text=Checkout%20the%20ColorPicker%20library%20for%20android.%20https%3A%2F%2Fgithub.com%2FDhaval2404%2FColorPicker%20)

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://twitter.com/Dhaval2404">Dhaval Patel</a> and
  <a href="https://github.com/dhaval2404/colorpicker/graphs/contributors">
    contributors
  </a>
</div>
<br/>

Yet another Color Picker Library for Android. It is highly customizable and easy to use. Pick the color from wheel or select Material Colors from dialog. The original ColorPickerView was written by [Hong Duan](https://github.com/duanhong169/ColorPicker).

# 🐱‍🏍Features:

* Color Picker View
* Color Picker Dialog with Recent Color Option
* Material Color Picker Alert Dialog
* Material Color Picker BottomSheet Dialog

# 🎬Preview


   Color Picker    |         Material Color Picker      |
:-------------------------:|:-------------------------:
![](https://github.com/Dhaval2404/ColorPicker/blob/master/art/colorpicker_demo.gif)  |  ![](https://github.com/Dhaval2404/ColorPicker/blob/master/art/materialcolorpicker_demo.gif)

# 💻Usage


1. Gradle dependency:

	```groovy
	implementation 'com.github.dhaval2404:colorpicker:1.2.1'
	```

2. The **ColorPicker** configuration is created using the builder pattern.

	```kotlin
    // Kotlin Code
    ColorPickerDialog
        .Builder(this)        				// Pass Activity Instance
        .setTitle("Pick Theme")           	// Default "Choose Color"
        .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
        .setDefaultColor(mDefaultColor)     // Pass Default Color
        .setColorListener { color, colorHex ->
        	// Handle Color Selection
        }
        .show()
    ```

    ```java
    // Java Code
    new ColorPickerDialog
        .Builder(this)
        .setTitle("Pick Theme")
        .setColorShape(ColorShape.SQAURE)
        .setDefaultColor(mDefaultColor)
        .setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, @NotNull String colorHex) {
                // Handle Color Selection
            }
        })
        .show();
    ````


3. The **MaterialColorPicker** configuration is created using the builder pattern.

	```kotlin
    // Kotlin Code
    MaterialColorPickerDialog
        .Builder(this)        					// Pass Activity Instance
        .setTitle("Pick Theme")           		// Default "Choose Color"
        .setColorShape(ColorShape.SQAURE)   	// Default ColorShape.CIRCLE
        .setColorSwatch(ColorSwatch._300)   	// Default ColorSwatch._500
        .setDefaultColor(mDefaultColor) 		// Pass Default Color
        .setColorListener { color, colorHex ->
       		// Handle Color Selection
        }
        .show()
    ```

    ```java
    // Java Code
    new MaterialColorPickerDialog
        .Builder(this)
        .setTitle("Pick Theme")
        .setColorShape(ColorShape.SQAURE)
        .setColorSwatch(ColorSwatch._300)
        .setDefaultColor(mDefaultColor)
        .setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, @NotNull String colorHex) {
            	// Handle Color Selection
            }
        })
        .show();
    ```

# 🎨Customization

 *  You can change the color or Positive and Negative Button Text Color. Add Following parameters in your **colors.xml** file.

     ```xml
     <resources>
         <!-- Here you can add color of your choice  -->
         <color name="positiveButtonTextColor">@color/colorAccent</color>
         <color name="negativeButtonTextColor">@color/colorAccent</color>
         <color name="bottomSheetDividerColor">#F5F5F5</color>
     </resources>
     ```

 *  You can provide predefine colors for the MaterialColorPicker

     ```java
     // Kotlin Code
     MaterialColorPickerDialog
		.Builder(activity!!)

        // Option 1: Pass Hex Color Codes
        //.setColors(arrayListOf("#f6e58d", "#ffbe76", "#ff7979", "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"))

        // Option 2: Pass Hex Color Codes from string.xml
        //.setColors(resources.getStringArray(R.array.themeColorHex))

        // Option 3: Pass color array from colors.xml
        .setColorRes(resources.getIntArray(R.array.themeColors))

        .setColorListener { color, colorHex ->
          // Handle Color Selection
        }
        .show()
	```

	```java
	// Java Code
	String[] colorArray = new String[]{"#f6e58d", "#ffbe76", "#ff7979",
	    "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"};

	new MaterialColorPickerDialog
	    .Builder(requireActivity())

	    // Option 1: Pass Hex Color Codes
	    //.setColors(colorArray)

	    // Option 2: Pass Hex Color Codes from string.xml
	    //.setColors(getResources().getStringArray(R.array.themeColorHex))

	    // Option 3: Pass color array from colors.xml
	    .setColorRes(getResources().getIntArray(R.array.themeColors))
	    
	    .setColorListener(object : ColorListener {
            override fun onColorSelected(color: Int, colorHex: String) {
                // Handle Color Selection
            }
        })
	    .show();
      ```

      Where **R.array.themeColors** and **R.array.themeColorHex** are defined as  below

      ```xml
      <array name="themeColors">
        <item>@color/green_500</item>
        <item>@color/blue_500</item>
        <item>@color/red_500</item>
        <item>@color/grey_500</item>
        <item>@color/orange_500</item>
      </array>

      <string-array name="themeColorHex">
        <item>#f6e58d</item>
        <item>#ffbe76</item>
        <item>#ff7979</item>
        <item>#badc58</item>
        <item>#dff9fb</item>
        <item>#7ed6df</item>
        <item>#e056fd</item>
        <item>#686de0</item>
        <item>#30336b</item>
        <item>#95afc0</item>
    </string-array>
      ```

# 💥Compatibility

  * Library - Android Lollipop 5.0+ (API 21)
  * Sample - Android Lollipop 5.0+ (API 21)

# ✔️Changelog

### Version: 1.2

  * Added Dark mode support & German translation [#4](https://github.com/Dhaval2404/ColorPicker/pull/4)

### Version: 1.1

  * Added option to change positive and negative button text color [#2](https://github.com/Dhaval2404/ImagePicker/issues/2)

### Version: 1.0

  * Initial Build

## 📃 Libraries Used
* ColorPicker [https://github.com/duanhong169/ColorPicker](https://github.com/duanhong169/ColorPicker)

### Let us know!

We'll be really happy if you sent us links to your projects where you use our component. Just send an email to **dhavalpatel244@gmail.com** And do let us know if you have any questions or suggestion regarding the library.

## License

    Copyright 2020, Dhaval Patel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
