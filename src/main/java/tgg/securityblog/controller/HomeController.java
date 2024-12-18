package tgg.securityblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tgg.securityblog.dto.home.HomeUserResponseDTO;
import tgg.securityblog.dto.post.HomePostsPagingResponseDTO;
import tgg.securityblog.dto.post.HomePostsResponseDTO;
import tgg.securityblog.entity.User;
import tgg.securityblog.service.ImageFileService;
import tgg.securityblog.service.PostService;
import tgg.securityblog.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ImageFileService imageFileService;
    private final PostService postService;

    @GetMapping("/")
    public String showHome(Authentication authentication, Model model, @PageableDefault(page = 0, size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){

        ifAuthenticatedSetResponseDTO(authentication, model);

        Page<HomePostsResponseDTO> posts = postService.findAllPosts(pageable);
        model.addAttribute("posts", posts);

        setPaging(posts, model);

        return "home/home";
    }

    private void setPaging(Page<HomePostsResponseDTO> posts,Model model){
        int totalPages = posts.getTotalPages();
        int startPage = (posts.getNumber() / 10) * 10;
        int endPage;
        if(startPage + 9 < totalPages){
            endPage = startPage + 9;
        }
        else{
            if(posts.getTotalPages() == 0){
                endPage = 0;
            }
            else{
                endPage = totalPages - 1;
            }
        }
        HomePostsPagingResponseDTO paging = new HomePostsPagingResponseDTO(startPage, endPage);
        model.addAttribute("paging", paging);
    }

    private void ifAuthenticatedSetResponseDTO(Authentication authentication, Model model){
        if(authentication != null && !authentication.getName().equals("anonymousUser") && authentication.isAuthenticated()) {
            User findUser = userService.findUserByUsername(authentication.getName());
            String savedFilename = imageFileService.getSavedFilename(findUser.getUsername());
            HomeUserResponseDTO user = new HomeUserResponseDTO(findUser.getNickname(), savedFilename);
            model.addAttribute("user", user);
        }
    }
}
