package nz.co.redice.newsfeeder.utils;

import androidx.annotation.NonNull;

public enum Category {

    TOPS_HEADLINES {
        @NonNull
        @Override
        public String toString() {
            return "Top Headlines";
        }
    },
    BUSINESS {
        @NonNull
        @Override
        public String toString() {
            return "Business";
        }
    },
    ENTERTAINMENT {
        @NonNull
        @Override
        public String toString() {
            return "Entertainment";
        }
    },
    HEALTH {
        @NonNull
        @Override
        public String toString() {
            return "Health";
        }
    },
    SCIENCE {
        @NonNull
        @Override
        public String toString() {
            return "Science";
        }
    },
    SPORTS {
        @NonNull
        @Override
        public String toString() {
            return "Sports";
        }
    },
    TECHNOLOGY {
        @NonNull
        @Override
        public String toString() {
            return "Technology";
        }
    }



}
