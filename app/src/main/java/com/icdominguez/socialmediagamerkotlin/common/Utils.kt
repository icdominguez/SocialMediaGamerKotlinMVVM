package com.icdominguez.socialmediagamerkotlin.common

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class Utils {
    fun createFileFromURI(context: Context, uri: Uri) : File {

        var photoFile = File(context.applicationInfo.dataDir)

        var inputStream = context.contentResolver.openInputStream(uri)

        var out: FileOutputStream? = null

        try {
            out = FileOutputStream(photoFile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        if(inputStream != null) {
            var count = 0
            val buffer = ByteArray(1024 * 4)
            val eof = -1
            var n = 0
            while(eof != (inputStream.read(buffer).also { n = it })) {
                out?.write(buffer, 0, n)
                count += n
            }

            inputStream.close()
        }

        out?.close()

        return photoFile
    }
}