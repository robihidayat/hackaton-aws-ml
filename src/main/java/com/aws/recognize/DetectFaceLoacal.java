package com.aws.recognize;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;

public class DetectFaceLoacal {
    public static void main(String[] args) throws Exception {

        String photo="path file";


        ByteBuffer imageBytes;
        InputStream inputStream = new FileInputStream(new File(photo));
        imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().build();

        DetectFacesRequest requestsFace = new DetectFacesRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withAttributes(Attribute.ALL);
        try {
            DetectFacesResult result = rekognitionClient.detectFaces(requestsFace);
            List < FaceDetail > faceDetails = result.getFaceDetails();

            for (FaceDetail face: faceDetails) {
                if (requestsFace.getAttributes().contains("ALL")) {
                    AgeRange ageRange = face.getAgeRange();
                    System.out.println("The detected face is estimated to be between "
                            + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
                            + " years old.");
                    System.out.println("gendernya adalah : " +face.getGender().getValue());
                    System.out.println("Here's the complete set of attributes:");
                }
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }

    }
}