// @ts-check
import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import cloudflare from '@astrojs/cloudflare';

// https://astro.build/config
export default defineConfig({
    integrations: [
        starlight({
            title: 'Minegun',
            social: [{ icon: 'github', label: 'GitHub', href: 'https://github.com/withastro/starlight' }],
            sidebar: [
                {
                    label: 'Guides',
                    items: [
                        // Each item here is one entry in the navigation menu.
                        { label: 'Example Guide', slug: 'guides/example' },
                        { label: 'Introduction', slug: 'guides/introduction'},
                    ],
                },
                {
                    label: 'Reference',
                    items: [{ autogenerate: { directory: 'reference' } }],
                },
            ],
        }),
    ],

    adapter: cloudflare(),

    // Add this Vite block to bypass the SSR path resolution bug
    vite: {
        ssr: {
            external: ['@expressive-code/core', 'postcss', 'path', 'node:path'],
        },
    },
});