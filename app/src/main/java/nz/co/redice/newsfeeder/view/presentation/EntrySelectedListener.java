package nz.co.redice.newsfeeder.view.presentation;

import nz.co.redice.newsfeeder.repository.local.dao.Entry;

public interface EntrySelectedListener {

    void onClick(Entry entry);
}
