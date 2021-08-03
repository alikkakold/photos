package com.sergioburik.photos.tools;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageCrop {

    @Value("${upload.path}")
    String uploadPath;

    public String saveSquareImage(MultipartFile file) throws IOException {
        BufferedImage original = ImageIO.read(file.getInputStream());
        //assuming we want a square thumbnail here
        int side = Math.min(original.getWidth(), original.getHeight());

        int x = (original.getWidth() - side) / 2;
        int y = (original.getHeight() - side) / 2;

        BufferedImage cropped = original.getSubimage(x, y, side, side);

        BufferedImage img = new BufferedImage(650, 650, BufferedImage.TYPE_INT_RGB);
        Image croppedImage = cropped.getScaledInstance(650, 650, Image.SCALE_SMOOTH);
        img.createGraphics().drawImage(croppedImage, 0, 0, null);

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        ImageIO.write(img, FilenameUtils.getExtension(file.getOriginalFilename()) ,
                new File(uploadPath + "/" + resultFilename));

        return resultFilename;
    }

}
