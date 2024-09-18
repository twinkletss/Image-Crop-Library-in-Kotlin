package org.twinkle.image_crop

import android.net.Uri

@Deprecated(
  """
  This ActivityResultContract is deprecated.
  Please either roll your own ActivityResultContract with the desired behavior or copy paste this.
""",
)
data class CropImageContractOptions(
  val uri: Uri?,
  val cropImageOptions: CropImageOptions,
)
