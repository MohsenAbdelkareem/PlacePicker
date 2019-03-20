	
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  	dependencies {
	        implementation 'com.github.MohsenAbdelkareem:PlacePicker:1.1.1'
	}
	
	
Use it:
try {
                Intent intentBuilder = new PlacePlacePicker.IntentBuilder()
                        .setAndroidApiKey(getResources().getString(R.string.google_maps_key))
                        .setGeolocationApiKey(getResources().getString(R.string.google_maps_key))
                        .build(this);
                startActivityForResult(intentBuilder, PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
	    
Get results:
protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePlacePicker.Companion.getPlace(data);
            }
        }
    }	    
	
	
To change style:
	
in style
<style name="PlaceTheme" parent="PlaceThemeBase">

        <item name="colorPrimary">@color/legacy_light_primary</item>
        <item name="colorPrimaryDark">@color/legacy_light_primary_dark</item>
        <item name="colorAccent">@color/app_text_color</item>
	<item name="colorButtonNormal">@color/colorPrimary</item>
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <item name="android:textColorSecondary">@color/textColorSecondary</item>

    </style>

in style (v21):
<style name="PlaceTheme" parent="PlaceThemeBase">
        <item name="colorPrimary">@color/legacy_light_primary</item>
        <item name="colorPrimaryDark">@color/legacy_light_primary_dark</item>
        <item name="colorAccent">@color/app_text_color</item>
	<item name="colorButtonNormal">@color/colorPrimary</item>
        <item name="android:textColorPrimary">@color/textColorPrimary</item>
        <item name="android:textColorSecondary">@color/textColorSecondary</item>
        <item name="buttonBarNegativeButtonStyle">
            @style/Widget.MaterialComponents.Button.TextButton.Dialog
        </item>
        <item name="buttonBarPositiveButtonStyle">
            @style/Widget.MaterialComponents.Button.TextButton.Dialog
        </item>
    </style>
