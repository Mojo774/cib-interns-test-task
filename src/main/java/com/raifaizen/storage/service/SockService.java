package com.raifaizen.storage.service;

import com.raifaizen.storage.models.Operation;
import com.raifaizen.storage.models.Sock;
import com.raifaizen.storage.repository.SockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SockService {

    @Autowired
    private SockRepository sockRepository;

    public List<Sock> getSocks(String color, String operation, int cottonPart) throws IllegalArgumentException, RuntimeException {
        if (cottonPart == -1 && color.equals(""))
            return sockRepository.findAll();

        if (cottonPart != -1 && !color.equals(""))
            return getSocksByColorAndCottonPart(color, Operation.valueOf(operation), cottonPart);

        if (cottonPart != -1) {

            return getSocksByCottonPart(Operation.valueOf(operation), cottonPart);

        } else {
            return sockRepository.findByColor(color);
        }
    }

    public void income(String color, int cottonPart, int quantity) {
        List<Sock> sockInList = getSocksByColorAndCottonPart(color, Operation.valueOf("equal"), cottonPart);
        Sock sock;

        if (!sockInList.isEmpty()) {
            sock = sockInList.get(0);
            sock.setQuantity(sock.getQuantity() + quantity);
        } else {
            sock = new Sock(color, cottonPart, quantity);
        }
        sockRepository.save(sock);
    }

    public void outcome(String color, int cottonPart, int quantity) throws RuntimeException {
        List<Sock> sockInList = getSocksByColorAndCottonPart(color, Operation.valueOf("equal"), cottonPart);

        Sock sock;

        if (!sockInList.isEmpty()) {
            sock = sockInList.get(0);
            int newQuantity = sock.getQuantity() - quantity;

            if (newQuantity < 0) {
                throw new RuntimeException();
            }

            if (newQuantity == 0) {
                sockRepository.delete(sock);

            } else {
                sock.setQuantity(newQuantity);
                sockRepository.save(sock);
            }

        } else {
            throw new RuntimeException();
        }
    }

    private List<Sock> getSocksByColorAndCottonPart(String color, Operation operation, int cottonPart) throws RuntimeException {
        switch (operation) {
            case equal:
                return sockRepository.findByColorAndCottonPartEqual(color, cottonPart);
            case lessThan:
                return sockRepository.findByColorAndCottonPartLess(color, cottonPart);
            case moreThan:
                return sockRepository.findByColorAndCottonPartMore(color, cottonPart);
        }
        throw new RuntimeException();
    }

    private List<Sock> getSocksByCottonPart(Operation operation, int cottonPart) throws RuntimeException {
        switch (operation) {
            case equal:
                return sockRepository.findByCottonPartEqual(cottonPart);
            case lessThan:
                return sockRepository.findByCottonPartLess(cottonPart);
            case moreThan:
                return sockRepository.findByCottonPartMore(cottonPart);
        }
        throw new RuntimeException();
    }

}
