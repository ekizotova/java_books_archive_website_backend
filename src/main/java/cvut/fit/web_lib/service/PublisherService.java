package cvut.fit.web_lib.service;

import cvut.fit.web_lib.entities.Book;
import cvut.fit.web_lib.entities.Publisher;
import cvut.fit.web_lib.repositories.BookRepository;
import cvut.fit.web_lib.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public Publisher addPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public List<Publisher> allPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(Long idPublisher) {
        return publisherRepository.findById(idPublisher).orElse(null);
    }

    @Transactional
    public void addBookToPublisher(Publisher publisher, Book book) {
        publisher.addBookToPublisher(book);
        publisherRepository.save(publisher);

        publisherRepository.save(publisher);
        bookRepository.save(book);
    }


    public Publisher updatePublisher(Long idPublisher, Publisher newTel) {
        Publisher oldTel = publisherRepository.findById(idPublisher).orElse(null);
        if (oldTel != null) {
            oldTel.setTel(newTel.getTel());

            return publisherRepository.save(oldTel);
        }
        return null;
    }

    public void deletePublisher(Long idPublisher) {
        publisherRepository.deleteById(idPublisher);
    }

    public boolean checkPublisherExists(String publName) {
        Optional<Publisher> existingPublisher = publisherRepository
                .findByPublName(publName);

        return existingPublisher.isPresent();
    }
}
