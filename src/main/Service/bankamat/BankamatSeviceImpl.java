package main.Service.bankamat;

import main.model.Bankamat;

import java.util.ArrayList;

public class BankamatSeviceImpl implements BankamatService {
    Long id = 0L;
    @Override
    public int create(Bankamat bankamat) {
        bankamat.setId(id++);
        bankamats.add(bankamat);
        return 1;
    }

    @Override
    public ArrayList<Bankamat> showAll() {
        return bankamats;
    }

    @Override
    public void delete(Long id) {
        for (Bankamat bankamat:bankamats) {
            if (bankamat.getId().equals(id)){
                bankamats.remove(bankamat);
                break;
            }
        }
    }

    @Override
    public Bankamat update(Bankamat update) {
        int i = 0;
        for (Bankamat bankamat:bankamats) {
            if (bankamat.getId().equals(update.getId())){
                bankamats.set(i,update);
                return bankamat;
            }
            i++;
        }
        return null;
    }

    @Override
    public Bankamat getById(Long id) {
        for (Bankamat bankamat:bankamats) {
            if (bankamat.getId().equals(id)){
                return bankamat;
            }
        }
        return null;
    }
}
