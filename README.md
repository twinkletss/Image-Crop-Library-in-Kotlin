Image crop library

First add jitpack io dependency in build.gradle file

dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }  //add this line in your project.
		}
	}


Add dependency in your build.gradle(app level)


dependencies {
	        implementation 'com.github.twinkletss:Image-Crop-Library-in-Kotlin:1.0.1'
	}

There are 3 ways of using the library. Check out the sample app for all details.
1. Calling crop directly

Note: This way is deprecated and will be removed in future versions. The path forward is to write your own Activity, handle all the Uri stuff yourself and use CropImageView.

class MainActivity : AppCompatActivity() {
  private val cropImage = registerForActivityResult(CropImageContract()) { result ->
    if (result.isSuccessful) {
      // Use the cropped image URI.
      val croppedImageUri = result.uriContent
      val croppedImageFilePath = result.getUriFilePath(this) // optional usage
      // Process the cropped image URI as needed.
    } else {
      // An error occurred.
      val exception = result.error
      // Handle the error.
    }
  }

  private fun startCrop() {
    // Start cropping activity with guidelines.
    cropImage.launch(
      CropImageContractOptions(
        cropImageOptions = CropImageOptions(
          guidelines = Guidelines.ON
        )
      )
    )

  // Start cropping activity with gallery picker only.
  cropImage.launch(
      CropImageContractOptions(
        pickImageContractOptions = PickImageContractOptions(
          includeGallery = true,
          includeCamera = false
        )
      )
    )

   // Start cropping activity for a pre-acquired image with custom settings.
  cropImage.launch(
      CropImageContractOptions(
        uri = imageUri,
        cropImageOptions = CropImageOptions(
          guidelines = Guidelines.ON,
          outputCompressFormat = Bitmap.CompressFormat.PNG
        )
      )
    )
  }

  // Call the startCrop function when needed.
}

2. Using CropView

Note: This is the only way forward, add CropImageView into your own activity and do whatever you wish. Checkout the sample for more details.

<!-- Image Cropper fill the remaining available height -->
<org.twinkle.image_crop.CropImageView
  android:id="@+id/cropImageView"
  android:layout_width="match_parent"
  android:layout_height="0dp"
  android:layout_weight="1"
  />

    Set image to crop

cropImageView.setImageUriAsync(uri)
// Or prefer using uri for performance and better user experience.
cropImageView.setImageBitmap(bitmap)

    Get cropped image

// Subscribe to async event using cropImageView.setOnCropImageCompleteListener(listener)
cropImageView.getCroppedImageAsync()
// Or.
val cropped: Bitmap = cropImageView.getCroppedImage()

3. Extend to make a custom activity

Note: This way is also deprecated and will be removed in future versions. The path forward is to write your own Activity, handle all the Uri stuff yourself and use CropImageView.

If you want to extend the CropImageActivity please be aware you will need to set up your CropImageView

  Add CropImageActivity into your AndroidManifest.xml
  
  <!-- Theme is optional and only needed if default theme has no action bar. -->
<activity
  android:name="org.twinkle.image_crop.CropImageActivity"
  android:theme="@style/Base.Theme.AppCompat"
  />

Set up your CropImageView after call super.onCreate(savedInstanceState)

override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setCropImageView(binding.cropImageView)
}

Custom dialog for image source pick

When calling crop directly the library will prompt a dialog for the user choose between gallery or camera (If you keep both enable). We use the Android default AlertDialog for this. If you wanna customised it with your app theme you need to override the method showImageSourceDialog(..) when extending the activity (above)

override fun showImageSourceDialog(openSource: (Source) -> Unit) {
  super.showImageSourceDialog(openCamera)
}

