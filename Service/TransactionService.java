package com.backend.librarymanagementsystem.Service;

import com.backend.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backend.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backend.librarymanagementsystem.Entity.Book;
import com.backend.librarymanagementsystem.Entity.LibraryCard;
import com.backend.librarymanagementsystem.Entity.Transaction;
import com.backend.librarymanagementsystem.Enum.CardStatus;
import com.backend.librarymanagementsystem.Enum.TransactionStatus;
import com.backend.librarymanagementsystem.Repository.BookRepository;
import com.backend.librarymanagementsystem.Repository.CardRepository;
import com.backend.librarymanagementsystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);


        LibraryCard card;
        try {
             card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card Id");

        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();

        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book Id");
        }

        transaction.setBook(book);
        transaction.setCard(card);

        //both card and book are valid
       if(card.getStatus() != CardStatus.ACTIVATED){
           transaction.setTransactionStatus(TransactionStatus.FAILED);
           transaction.setMessage("Your Card is not Activated");
           transactionRepository.save(transaction);
           throw new Exception("Your Card is not Activated");
       }
       if(book.isIssued()==true){
           transaction.setTransactionStatus(TransactionStatus.FAILED);
           transaction.setMessage("Sorry, Book is already Issued");
           transactionRepository.save(transaction);
           throw new Exception("Sorry, Book is already Issued");
       }

       //here i can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was successfull");
        book.setIssued(true);
       book.setCard(card);
       book.getTransactionList().add(transaction);
       card.getTransactionList().add(transaction);
       card.getBooksIsuued().add(book);

       cardRepository.save(card); //will save book and transaction also because card is parent for both book and transaction.

       IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
       issueBookResponseDto.setBookName(book.getTitle());
       issueBookResponseDto.setTansactionId(transaction.getTransactionNumber());
       issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);

       return issueBookResponseDto;
    }

    public String getAllTxns(int cardId){
        List<Transaction> transactionList = transactionRepository.getAllSuccessfullTxnsWithCardNo(cardId);
        String ans = "";
        for(Transaction t: transactionList){
            ans += t.getTransactionNumber();
            ans += "\n";
        }

        return ans;
    }
}
