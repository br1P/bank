package org.example.MyBatis.Service;

import org.example.MyBatis.DAOS.BankerMapper;
import org.example.model.Banker;

import java.util.List;

public class BankerService {
    private final BankerMapper bankerMapper;

    public BankerService(BankerMapper bankerMapper) {
        this.bankerMapper = bankerMapper;
    }

    public void createBanker(Banker banker) {
        bankerMapper.insertBanker(banker);
    }

    public Banker getBankerById(int bankerID) {
        return bankerMapper.selectBankerById(bankerID);
    }

    public List<Banker> getAllBankers() {
        return bankerMapper.selectAllBankers();
    }

    public void updateBanker(Banker banker) {
        bankerMapper.updateBanker(banker);
    }

    public void deleteBanker(int bankerID) {
        bankerMapper.deleteBanker(bankerID);
    }
}
