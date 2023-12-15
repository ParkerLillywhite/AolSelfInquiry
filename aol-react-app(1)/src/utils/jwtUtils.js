
 export function setJwtCookie(response) {
    try {
        const token = response.token;
        const expires = new Date(Date.now() + 1000 * 60 * 60 * 24);
        document.cookie = `jwtToken=${token}; expires=${expires.toUTCString()}; path=/`;
    } catch (error) {
        console.log("Error setting new cookie: ", error);
    }

}

export function getJwtCookie() {
    const name = 'jwtToken=';
    const decodedCookie = decodeURIComponent(document.cookie);
    const cookieArray = decodedCookie.split(';');

    for(let i = 0; i < cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while(cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if(cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }  
    }
    return null;
}

export function clearJwtCookie() {
    document.cookie = 'jwtToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'
}


// const data = await response.json(); --- use this to use the cookie in desired spot.
// setJwtCookie(data);

