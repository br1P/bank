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

    @Select("""
        SELECT 
            b.BankerID,
            p.Name,
            p.LastName,
            b.LicenseNumber,
            b.Salary,
            d.DepartmentName
        FROM 
            Banker b
        JOIN 
            Person p ON b.PersonID = p.PersonID
        JOIN 
            Department d ON b.DepartmentID = d.DepartmentID
    """)
    List<Banker> selectAllBankers();

    @Update("UPDATE Banker SET licenseNumber = #{licenseNumber}, departmentID = #{departmentID}, salary = #{salary} WHERE bankerID = #{bankerID}")
    void updateBanker(Banker banker);

    @Delete("DELETE FROM Banker WHERE bankerID = #{bankerID}")
    void deleteBanker(int bankerID);
}
