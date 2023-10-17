package africa.semicolon.gemstube.services;

import africa.semicolon.gemstube.dtos.request.UploadMediaRequest;
import africa.semicolon.gemstube.dtos.response.UploadMediaResponse;
import africa.semicolon.gemstube.exceptions.GemsTubeException;
import africa.semicolon.gemstube.models.Media;
import africa.semicolon.gemstube.models.User;
import africa.semicolon.gemstube.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GemsTubeMediaService implements MediaService{
    private final CloudService cloudService;
    private final MediaRepository mediaRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    @Override
    public UploadMediaResponse upload(UploadMediaRequest uploadMediaRequest) throws GemsTubeException {
        User user = userService.getUserById(uploadMediaRequest.getCreatorId());
        String url = cloudService.upload(uploadMediaRequest.getMultipartFile());
        Media media = modelMapper.map(uploadMediaRequest, Media.class);
        media.setUrl(url);
        media.setUploader(user);
        Media savedMedia = mediaRepository.save(media);
        return buildUploadMediaResponse(savedMedia);
    }

    private static UploadMediaResponse buildUploadMediaResponse(Media media){
        UploadMediaResponse  response = new UploadMediaResponse();
        response.setMessage("Media upload successful");
        response.setMediaId(media.getId());
        return response;
    }

    @Override
    public Media getMediaById(Long id) throws GemsTubeException {
        return mediaRepository.findById(id).orElseThrow( ()-> new GemsTubeException(String.format("Media with id %d not found", id)));

    }
}
