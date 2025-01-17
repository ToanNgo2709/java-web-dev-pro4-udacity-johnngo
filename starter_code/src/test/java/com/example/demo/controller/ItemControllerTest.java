package com.example.demo.controller;

import com.example.demo.controllers.ItemController;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.TestUtils.createItem;
import static com.example.demo.utils.TestUtils.createItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup(){

        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(1)));
        when(itemRepository.findAll()).thenReturn(createItems());
        when(itemRepository.findByName("item")).thenReturn(Arrays.asList(createItem(1), createItem(2)));

    }

    @Test
    public void verify_getItems(){
        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = response.getBody();

        assertEquals(createItems(), items);

        verify(itemRepository , times(1)).findAll();
    }

    @Test
    public void verify_getItemById(){

        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item item = response.getBody();
        assertEquals(createItem(1L), item);

        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    public void verify_getItemByIdInvalid(){

        ResponseEntity<Item> response = itemController.getItemById(10L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        assertNull(response.getBody());
        verify(itemRepository, times(1)).findById(10L);
    }

    @Test
    public void verify_getItemByName(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> items = Arrays.asList(createItem(1), createItem(2));

        assertEquals(createItems(), items);

        verify(itemRepository , times(1)).findByName("item");
    }

    @Test
    public void verify_getItemByNameInvalid(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("invalid name");

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

        assertNull(response.getBody());

        verify(itemRepository , times(1)).findByName("invalid name");
    }
}
