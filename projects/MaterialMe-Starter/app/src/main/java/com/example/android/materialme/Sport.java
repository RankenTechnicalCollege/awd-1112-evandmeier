/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.materialme;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data model for each row of the RecyclerView
 */
public class Sport implements Parcelable {

    // Member variables representing the title and information about the sport.
    private final String title;
    private final String info;
    private final String league;
    private final String teams;
    private final int imageResource;

    /**
     * Constructor for the Sport data model.
     *
     * @param title The name if the sport.
     * @param info Information about the sport.
     */
    public Sport(String title, String info, String teams, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
        this.league = "Major League SportName";
        this.teams = teams;
    }

    protected Sport(Parcel in) {
        title = in.readString();
        info = in.readString();
        league = in.readString();
        teams = in.readString();
        imageResource = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(info);
        dest.writeString(league);
        dest.writeString(teams);
        dest.writeInt(imageResource);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sport> CREATOR = new Creator<Sport>() {
        @Override
        public Sport createFromParcel(Parcel in) {
            return new Sport(in);
        }

        @Override
        public Sport[] newArray(int size) {
            return new Sport[size];
        }
    };

    /**
     * Gets the title of the sport.
     *
     * @return The title of the sport.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the info about the sport.
     *
     * @return The info about the sport.
     */
    public String getInfo() {
        return info;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getLeague() {
        return league;
    }

    public String getTeams() {
        return teams;
    }
}
