'use strict';
const storage = {
    get(repositoryType, key) {
        return window[`${repositoryType}`].getItem(key);
    },
    set(repositoryType, key, value) {
        return window[`${repositoryType}`].setItem(key, value);
    },
    delete(repositoryType, key) {
        return window[`${repositoryType}`].removeItem(key);
    },
    clear(repositoryType) {
        return window[`${repositoryType}`].clear();
    }
};

export function getSession(key) {
    return storage.get('sessionStorage', key);
}

export function setSession(key, value) {
    storage.set('sessionStorage', key, value);
}

export function deleteSession(key) {
    storage.delete('sessionStorage', key);
}

export function clearSession() {
    storage.clear('sessionStorage');
}

export function getLocal(key) {
    return storage.get('localStorage', key);
}

export function setLocal(key, value) {
    storage.set('localStorage', key, value);
}

export function deleteLocal(key) {
    storage.delete('localStorage', key);
}

export function clearLocal() {
    storage.clear('localStorage');
}

export function getCookie(name) {
    // const cookies = document.cookie.split(';').map(c => c.trim()).filter(c => c.startsWith(`${name}=`));
    // console.info(cookies);
    const arr = document.cookie.match(RegExp(`${name}=([^;]+)`));
    return arr ? arr[1] : null;
}
