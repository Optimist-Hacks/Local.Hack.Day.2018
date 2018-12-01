package ledmein.service;

import ledmein.repository.DefaultEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GitHubService {

    private final DefaultEventRepository defaultEventRepository;

    @Autowired
    public GitHubService(DefaultEventRepository defaultEventRepository) {
        this.defaultEventRepository = defaultEventRepository;
    }

    @PostConstruct
    public void post() {
        defaultEventRepository.getCommits("https://api.github.com/repos/polis-mail-ru/2018-highload-kv");
    }

}
