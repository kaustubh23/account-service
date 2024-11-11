package account.service.current.mapper;

import account.service.current.dto.AccountDTO;
import account.service.current.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO accountToAccountDTO(Account account);


    Account accountDTOToAccount(AccountDTO accountDto);


}
