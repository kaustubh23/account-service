package account.service.current.mapper;

import account.service.current.dto.TransactionDTO;
import account.service.current.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO transactionToTransactionDTO(Transaction transaction);
    List<TransactionDTO> listTransactionToTransactionDTO(List<Transaction> list);
    Transaction transactionDtoTotransaction(TransactionDTO transactionDto);
}
