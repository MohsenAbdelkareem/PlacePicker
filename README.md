	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	dependencies {
	        implementation 'com.github.MohsenAbdelkareem:PlacePicker:1.1.1'
	}
	
	
To change style:
	
in style
<style name="PlaceTheme" parent="PlaceThemeBase">

        <item name="colorPrimary">@color/legacy_light_primary</item>
        <item name="colorPrimaryDark">@color/legacy_light_primary_dark</item>
        <item name="colorAccent">@color/app_text_color</item>
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <item name="android:textColorSecondary">@color/textColorSecondary</item>

    </style>

in style (v21):
<style name="PlaceTheme" parent="PlaceThemeBase">
        <item name="colorPrimary">@color/legacy_light_primary</item>
        <item name="colorPrimaryDark">@color/legacy_light_primary_dark</item>
        <item name="colorAccent">@color/app_text_color</item>
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <item name="android:textColorSecondary">@color/textColorSecondary</item>
        <item name="buttonBarNegativeButtonStyle">
            @style/Widget.MaterialComponents.Button.TextButton.Dialog
        </item>
        <item name="buttonBarPositiveButtonStyle">
            @style/Widget.MaterialComponents.Button.TextButton.Dialog
        </item>
    </style>
