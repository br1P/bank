package org.example.MyBatis.DAOS;

import org.example.model.Banker;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BankerMapper {

    @Insert("INSERT INTO Banker(personID, licenseNumber, departmentID, salary) VALUES(#{person.personID}, #{licenseNumber}, #{departmentID}, #{salary})")
    @Options(useGeneratedKeys = true, keyProperty = "bankerID")
    void insertBanker(Banker banker);

    @Select("SELECT * FROM Banker WHERE bankerID = #{bankerID}")
    Banker selectBankerById(int bankerID);

    @Select("SELECT * FROM Banker")
    List<Banker> selectAllBankers();

    @Update("UPDATE Banker SET licenseNumber = #{licenseNumber}, departmentID = #{departmentID}, salary = #{salary} WHERE bankerID = #{bankerID}")
    void updateBanker(Banker banker);

    @Delete("DELETE FROM Banker WHERE bankerID = #{bankerID}")
    void deleteBanker(int bankerID);
}
