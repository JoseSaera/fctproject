export interface MovieDTO {
    adult: boolean;
    backdrop_path: string;
    genre_ids: number[];
    id: number;
    original_language: string;
    original_title: string;
    overview: string;
    popularity: number;
    poster_path: string;
    release_date: string;
    title: string;
    video: boolean;
    vote_average: number;
    vote_count: number;
    poster_url: string;
    genresDTO: GenreDTO[];
}

export interface GenreDTO {
    id: number;
    name: string;
}

export interface YearRangeDTO {
    minYear: number;
    maxYear: number;
}

export interface MovieCountByYear {
    releaseYear: number;
    movieCount: number;
}

export interface AuthenticationResponse {
    id: number;
    token: string;
    seeLater: number[];
    favorites: number[];
}

export interface User {
    id: number;
    username: string;
    token: string;
    favorites: number[];
    seeLater: number[];
}
