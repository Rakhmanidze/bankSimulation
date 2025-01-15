package cz.cvut.fel.omo.bank.service;

import cz.cvut.fel.omo.bank.dao.BankDao;
import cz.cvut.fel.omo.bank.model.Bank;

import java.util.List;
import java.util.Optional;

public class BankService {
    private final BankDao dao;

    public BankService(BankDao bankDao) {
        this.dao = bankDao;
    }

    public Optional<Bank> getBank(Long id) {
       return dao.get(id);
    }

    public void createBank(Bank bank) {
        dao.create(bank);
    }

    public void updateBank(Bank bank) {
        dao.update(bank);
    }

    public void deleteBank(Long id) {
        dao.delete(id);
    }

    public List<Bank> getAllBanks() {
        return dao.getAll();
    }

    public Optional<Bank> getBankByName(String name) {
        return dao.findByName(name);
    }
}