package com.stella223.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, String price, String username){
        Item item = new Item(title, Integer.parseInt(price), username);
        itemRepository.save(item);
    }

    public void editItem(Integer id, String title, String price) throws Exception {
        if(title.length() > 20) throw new Exception("제목 너무 길어");

        Optional<Item> optionalItem = itemRepository.findById(id);

        if (optionalItem.isPresent()) {
            // 아이템이 존재하면 수정
            Item item = optionalItem.get();
            item.setTitle(title); // 새로운 제목으로 변경
            item.setPrice(Integer.parseInt(price)); // 새로운 가격으로 변경

            // 수정된 객체를 저장
            itemRepository.save(item);
        } else {
            // 존재하지 않을 경우 예외 처리
            throw new IllegalArgumentException("Item with ID " + id + " does not exist.");
        }

    }

    public List<Item> findAllItem() {
        List<Item> result = itemRepository.findAll();
        return result;
    }

    public Optional<Item> findItemById(Integer id){
        return itemRepository.findById(id);
    }

}
