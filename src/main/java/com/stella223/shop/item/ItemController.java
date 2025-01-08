package com.stella223.shop.item;

import com.stella223.shop.notice.Notice;
import com.stella223.shop.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {//API를 만들 수 있음

    private final ItemRepository itemRepository;
    private final NoticeRepository noticeRepository;
    private final ItemService itemService;
    private final S3Service s3Service;

    @GetMapping("/list")
    String redirectToPageOne() {
        return "redirect:/page/1";
    }

    @GetMapping("/notice")
    String noticeList(Model model) {
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }

    @GetMapping("/write")
    String write(Model model, Authentication auth) {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@RequestParam String title, String price, String fileURL, Authentication auth) {
        Item item = itemService.saveItem(title, price, auth.getName(), fileURL);
        Page<Item> result = itemRepository.findPageBy(PageRequest.of((int) (item.id - 1), 5));

        return "redirect:/page/"+ result.getTotalPages();
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Integer id, Model model) {
        Optional<Item> item = itemService.findItemById(id);
        if (item.isPresent()) {
            model.addAttribute("data", item.get());
            return "edit.html";
        } else {
            return "redirect: /list";
        }
    }

    @PostMapping("/edit")
    String editItem(Integer id, String title, String price) {
        try {
            itemService.editItem(id, title, price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model) {
        Optional<Item> result = itemService.findItemById(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            return "detail.html";
        } else {
            return "redirect:/list";
        }
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Integer id) {

        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/page/{id}")
    String listPage(@PathVariable Integer id, Model model) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(id - 1, 5));

        model.addAttribute("items", result);
        if(id > result.getTotalPages()){
            return "redirect:/page/"+ result.getTotalPages();
        }
        model.addAttribute("currentPage", id);
        model.addAttribute("totalPages", result.getTotalPages());

        return "list.html";//메인페이지 접속하면 이거 보내주세요
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

}
