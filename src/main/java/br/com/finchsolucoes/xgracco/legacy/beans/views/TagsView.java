package br.com.finchsolucoes.xgracco.legacy.beans.views;

import br.com.finchsolucoes.xgracco.domain.entity.Tag;

import java.util.List;

/**
 * Created by jordano on 28/04/17.
 */
public class TagsView {

    private List<Tag> tagList;

    private List<Tag> tagRemover;

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Tag> getTagRemover() {
        return tagRemover;
    }

    public void setTagRemover(List<Tag> tagRemover) {
        this.tagRemover = tagRemover;
    }
}
