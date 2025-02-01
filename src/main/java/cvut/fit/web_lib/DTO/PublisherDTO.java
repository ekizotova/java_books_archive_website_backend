package cvut.fit.web_lib.DTO;

import cvut.fit.web_lib.entities.Publisher;
import lombok.Data;

@Data
public class PublisherDTO {
    private Long idPublisher;
    private String publName;
    private String telNumber;
    private String location;

    public PublisherDTO(Publisher publisher) {
        this.idPublisher = publisher.getIdPusblisher();
        this.publName = publisher.getPublName();
        this.telNumber = publisher.getTelNumber();
        this.location = publisher.getLocation();

    }
    public PublisherDTO() {

    }

}
