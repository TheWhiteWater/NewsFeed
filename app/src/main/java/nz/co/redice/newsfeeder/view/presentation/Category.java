package nz.co.redice.newsfeeder.view.presentation;

import androidx.annotation.NonNull;

public enum Category {

    TOPS_HEADLINES {
        @NonNull
        @Override
        public String toString() {
            return "Top News";
        }
        public String getTag() {return "";}
    },
    BUSINESS {
        @NonNull
        @Override
        public String toString() {
            return "Business";
        }
        public String getTag() {return "business";}

    },
    ENTERTAINMENT {
        @NonNull
        @Override
        public String toString() {
            return "Entertainment";
        }
        public String getTag() {return "entertainment";}

    },
    HEALTH {
        @NonNull
        @Override
        public String toString() {
            return "Health";
        }
        public String getTag() {return "health";}

    },
    SCIENCE {
        @NonNull
        @Override
        public String toString() {
            return "Science";
        }
        public String getTag() {return "science";}

    },
    SPORTS {
        @NonNull
        @Override
        public String toString() {
            return "Sports";
        }
        public String getTag() {return "sports";}

    },
    TECHNOLOGY {
        @NonNull
        @Override
        public String toString() {
            return "Technology";
        }
        public String getTag() {return "technology";}

    };

    public abstract String getTag();



}
