package io.pivotal.pal.tracker;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    List<TimeEntry> timeEntry = new ArrayList<>();

    long id = 0l;

    public TimeEntry create(TimeEntry any) {

        long idCreated = incrementId(this.id);

        TimeEntry created = new TimeEntry(idCreated, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

        timeEntry.add(created);
        return created;
    }

    public TimeEntry find(long timeEntryId) {
        return timeEntry.stream().filter(it -> it.getId() == timeEntryId).findFirst().orElse(null);
    }

    public List<TimeEntry> list() {
        return timeEntry;
    }

    public TimeEntry update(long eq, TimeEntry any) {

        try {
            TimeEntry exist = timeEntry.stream().filter(it -> it.getId() == eq).collect(Collectors.toList()).get(0);
            TimeEntry result = new TimeEntry(eq, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

            timeEntry.remove(exist);
            timeEntry.add(result);
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    public void delete(long timeEntryId) {
        TimeEntry result = timeEntry.stream().filter(it -> it.getId() == timeEntryId).collect(Collectors.toList()).get(0);
        timeEntry.remove(result);
    }

    private long incrementId(long id) {
        return this.id = id + 1L;
    }
}
