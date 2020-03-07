package com.snupa.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@Component
class S3Uploader(
        @Autowired
        private val amazonS3Client: AmazonS3Client,
        @Value("\${cloud.aws.s3.bucket}")
        private val bucket: String,
        @Value("\${cloud.aws.s3.dir}")
        private val dirName: String
) {
    @Throws(IOException::class)
    fun upload(multipartFile: MultipartFile): String? {
        val uploadFile: File = convert(multipartFile)
                .orElseThrow { IllegalArgumentException("Converting MultipartFile to File failed") }
        return upload(uploadFile)
    }

    private fun upload(uploadFile: File): String? {
        val fileName = dirName + "/" + uploadFile.name
        val uploadImageUrl = putS3(uploadFile, fileName)
        removeNewFile(uploadFile)
        return uploadImageUrl
    }

    private fun putS3(uploadFile: File?, fileName: String?): String? {
        amazonS3Client.putObject(PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead))
        return amazonS3Client.getUrl(bucket, fileName).toString()
    }

    private fun removeNewFile(targetFile: File) {
        targetFile.delete()
    }

    @Throws(IOException::class)
    private fun convert(file: MultipartFile): Optional<File> {
        val convertFile = File(file.originalFilename!!)
        if (convertFile.createNewFile()) {
            FileOutputStream(convertFile).use { it.write(file.bytes) }
            return Optional.of(convertFile)
        }
        return Optional.empty()
    }
}