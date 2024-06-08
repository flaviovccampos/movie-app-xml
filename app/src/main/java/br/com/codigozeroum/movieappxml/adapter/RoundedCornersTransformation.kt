package br.com.codigozeroum.movieappxml.adapter

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import com.squareup.picasso.Transformation

class RoundedCornersTransformation (private val radius: Float) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        paint.isAntiAlias = true

        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawRoundRect(RectF(0f, 0f, source.width.toFloat(), source.height.toFloat()), radius, radius, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, 0f, 0f, paint)

        source.recycle()
        return output
    }

    override fun key(): String {
        return "rounded_corners_$radius"
    }

}