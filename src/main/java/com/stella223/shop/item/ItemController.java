package com.stella223.shop.item;

import com.stella223.shop.notice.Notice;
import com.stella223.shop.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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

    @GetMapping("/list")
    String itemList(Model model) {
        List<Item> result = itemService.findAllItem();
        model.addAttribute("items", result);

        return "list.html";//메인페이지 접속하면 이거 보내주세요
    }

    @GetMapping("/notice")
    String noticeList(Model model) {
        List<Notice> result = noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }

    @GetMapping("/write")
    String write(Model model) {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@RequestParam String title, String price, Authentication auth) {
        itemService.saveItem(title, price, auth.getName());
        return "redirect:/list";
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
            model.addAttribute("data", result.get());
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
        model.addAttribute("currentPage", id);
        model.addAttribute("totalPages", result.getTotalPages());

        return "list.html";//메인페이지 접속하면 이거 보내주세요
    }

}
