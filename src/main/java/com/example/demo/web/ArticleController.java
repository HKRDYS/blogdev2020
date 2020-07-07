package com.example.demo.web;

import com.example.demo.dao.IUserMapper;
import com.example.demo.model.entity.Article;
import com.example.demo.model.entity.Type;
import com.example.demo.model.entity.User;
import com.example.demo.service.IArticleService;
import com.example.demo.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import com.example.demo.service.impl.ArticleServiceImpl;

@Controller
public class ArticleController {
    @Autowired
    private IUserMapper iUserMapper;
    @Autowired
    private IArticleService iArticleService;
    @Autowired
    private ITypeService iTypeService;

    @RequestMapping("/guest/detail")
    public String detail(String article_id) {
        System.out.println("文章id" + article_id);
        return "/guest/article-detail";
    }

    @RequestMapping("/guest/findmore")
    public String findMore() {
        return "guest/allArticleList";
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping("/user/toPublishArticle")
    public String toPublishArticle(HttpServletRequest request) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getUsername());
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        request.getSession().setAttribute("user", user);
        //查一下文章类型
        List<Type> list = iTypeService.findType();
        request.setAttribute("typeList", list);
        return "article/publishArticle";
    }

    //发布博文
    @RequestMapping("/user/publisArticle")
    public String publishArticle(HttpServletRequest request, Article article, MultipartFile imgFile, String typeid) throws IOException {
        article.setPublishTime(new Date());
        article.setStatus("1");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = iUserMapper.findByLoginName(userDetails.getUsername());
        article.setUser(user);
        InputStream in = imgFile.getInputStream();
        ByteBuffer nbf = ByteBuffer.allocate((int) imgFile.getSize());
        byte[] array = new byte[1024];
        int offset = 0;
        int length = 0;
        while ((length = in.read(array)) > 0) {
            if (length != 1024) {
                nbf.put(array, 0, length);
            } else {
                nbf.put(array);
            }
            offset += length;
            break;
        }
        in.close();
        byte[] content = nbf.array();
        article.setImg(content.toString());

        Type type = new Type();
        type.setId(Integer.parseInt(typeid));
        article.setType(type);
        String msg = iArticleService.publishArticle(article);
        request.setAttribute("publishMsg", msg);
        return "article/publishArticle";
    }
}
