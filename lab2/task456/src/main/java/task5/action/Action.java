package task5.action;

import java.io.IOException;
import java.util.List;

public interface Action {
    void update(List<Long> numbers) throws IOException;
}
