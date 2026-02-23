package com.sprint.mission.discodeit.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

// todo: 프로필 사진이나 메시지 첨부파일 저장하는 용도...
@Component
@RequiredArgsConstructor
public class AttachmentSaveUtil {

//  private List<MultipartFile> attachments;

  public void saveAttachment(List<MultipartFile> attachments) {
    attachments.forEach(attachment -> {
      File uploadDest = new File(
          Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "images",
              attachment.getOriginalFilename()).toString());

      // 얘는 반복문 앞부분에 빼놓기
      if (!uploadDest.getParentFile().exists()) {
        uploadDest.getParentFile().mkdirs();
      }

      try {
        attachment.transferTo(new File(uploadDest.toString()));
        // 이하 주석은 service 레이어에서 직접 수행해야 할 부분
//        BinaryContent binaryContent = new BinaryContent(
//            newUser.getId(),
//            null,
//            attachment.getOriginalFilename()
//        );
//        binaryContentRepository.save(binaryContent);
//        newUser.updateProfileId(binaryContent.getId()); // user에 프로필 정보 업데이트
      } catch (
          IOException e) {
        throw new RuntimeException(e);
      }
    });


  }
}
