package common.entity;

import lombok.Data;

import java.util.List;

/**
 * School
 *
 * @author qrXun on 2020/11/14
 */
@Data
public class School {

    private List<Klass> klassList;

    @Override
    public String toString() {
        return "School{" +
                "klassList=" + klassList +
                '}';
    }
}
